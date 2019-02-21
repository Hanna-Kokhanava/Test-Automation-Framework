package com.kokhanava.automation.core.tools.files;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created on 01.04.2018
 */
public abstract class FileManager {

    private static Map<HostMachine, FileManager> managerMap = new HashMap<>();
    private final HostMachine hostMachine;

    FileManager(HostMachine hostMachine) {
        this.hostMachine = hostMachine;
    }

    /**
     * Returns instance of FileManager depending on the hostmachine placing
     *
     * @param hostMachine {@link HostMachine}
     * @return returns {@link FileManager} instance
     */
    public static FileManager getInstance(HostMachine hostMachine) {
        if (!managerMap.containsKey(hostMachine)) {
            FileManager instance = hostMachine.isRemote() ? new RemoteFileManager(hostMachine) : new LocalFileManager();
            managerMap.put(hostMachine, instance);
        }
        return managerMap.get(hostMachine);
    }

    /**
     * Downloads file from the specified URL
     * If URL is secured (HTTPS) - TEMPORARY solution is to disable certificates validation
     *
     * @param fileUrl  file url
     * @param filePath path to file in local system
     */
    public void downloadFileFromUrl(String fileUrl, String filePath) {
        Logger.debug("Downloading file from [" + fileUrl + "]");

        try (ReadableByteChannel channel = Channels.newChannel(new URL(fileUrl).openStream());
             FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
            outputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
        } catch (SSLHandshakeException e) {
            //TODO infinite recursive call ?
            Logger.warn("SSL Exception was thrown, execute stub to disable certificates validation and try to download once again");
            disableCertificateValidation();
            downloadFileFromUrl(fileUrl, filePath);
        } catch (IOException e) {
            Logger.error("Exception occurred while channel creation\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Disables all certificate validation
     * IS NOT RECOMMENDED!!!
     */
    //TODO to be removed in the future as it's not perfect solution
    private void disableCertificateValidation() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            Logger.error("Security exception is occurred during SSL certificates installation");
        }
    }


    /**
     * Unzip files into the destination folder
     * Also works with the subdirectories
     *
     * @param zipFilePath path to zip file
     * @param destPath    destination path for unzipped files
     */
    public void unzipFile(String zipFilePath, String destPath) {
        Logger.debug("Unzipping file " + zipFilePath);
        String filePath;
        File newFile;

        try (FileInputStream fileInputStream = new FileInputStream(new File(zipFilePath));
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)
        ) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (Objects.nonNull(zipEntry)) {
                filePath = destPath + File.separator + zipEntry.getName();
                newFile = new File(filePath);

                if (zipEntry.isDirectory()) {
                    new File(filePath).mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    extractFile(zipInputStream, filePath);
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            Logger.error("Exception occurred while unzipping process\n" + e.getMessage());
            e.printStackTrace();
        }

        new File(zipFilePath).delete();
    }

    /**
     * Search recursively for file by its name
     *
     * @param rootDirectory {@link File} root directory to search
     * @param fileName      name of file to find
     * @return absolute path to file
     */
    @Nullable
    public String getPathToFile(File rootDirectory, String fileName) {
        File[] files = rootDirectory.listFiles();
        Objects.requireNonNull(files, "Files are not found in [" + rootDirectory + "] directory");
        String filePath = null;
        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
                return file.getAbsolutePath();
            } else if (file.isDirectory()) {
                filePath = getPathToFile(file, fileName);
            }
        }
        return filePath;
    }

    /**
     * Extract files in specified directory
     *
     * @param zipInputStream {@link ZipInputStream} instance
     * @param filePath       path to zip entry
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = zipInputStream.read(buffer)) > 0) {
            bufferedOutputStream.write(buffer, 0, len);
        }
        bufferedOutputStream.close();
    }

    /**
     * Creates nonexistent parent directories
     *
     * @param pathToDirectory directories to create
     */
    public void createSubDirectories(String pathToDirectory) {
        try {
            Files.createDirectories(Paths.get(pathToDirectory));
        } catch (IOException e) {
            Logger.error("Unable to create nonexistent parent directories\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Copy file to folder
     *
     * @param targetFolderPath to which folder file must be copied
     * @param sourceFile       {@link File} which local file must be copied
     * @param targetFilename   how copied file must be named
     */
    abstract void copyFile(String targetFolderPath, File sourceFile, @Nonnull String targetFilename);

    public void copyFile(ResultFolder targetFolder, File sourceFile, @Nonnull String targetFilename) {
        copyFile(targetFolder.getPathToFolder(hostMachine), sourceFile, targetFilename);
    }

    /**
     * Check what file exist and same on machine.
     *
     * @param folderPath in which folder need to check file
     * @param file       verifiable file must be the same as local {@link File}
     * @param filename   how verifiable file must be named
     * @return {@code true} if verifiable file have the same filename and file size
     */
    abstract boolean isFileExist(String folderPath, File file, String filename);

    public boolean isFileExist(ResultFolder targetFolder, File sourceFile, String filename) {
        return isFileExist(targetFolder.getPathToFolder(hostMachine), sourceFile, filename);
    }

    /**
     * Remove file in root directory on remote machine
     *
     * @param folderPath in which folder need to remove file
     * @param filename   which file must be removed
     */
    abstract void removeFile(String folderPath, @Nonnull String filename);

    public void removeFile(ResultFolder folder, @Nonnull String filename) {
        removeFile(folder.getPathToFolder(hostMachine), filename);
    }

    public static BufferedWriter getFileWriter(File file, OpenOption... options) throws IOException {
        if (Objects.isNull(file) || file.isDirectory()) {
            Logger.error("Cannot append to file");
            throw new RuntimeException("File is failed");
        }
        return Files.newBufferedWriter(Paths.get(file.toURI()), Charset.forName("UTF8"), options);
    }
}
