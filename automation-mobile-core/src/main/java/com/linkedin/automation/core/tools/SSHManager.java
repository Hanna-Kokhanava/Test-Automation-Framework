package com.linkedin.automation.core.tools;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

public class SSHManager {
    private Session session;
    private JSch jsch;
    private HostMachine hostMachine;

    private final int DEFAULT_PORT = 22;

    public SSHManager(HostMachine machine) {
        jsch = new JSch();
        hostMachine = machine;
    }

    /**
     * Connect to remote host {@link HostMachine}.
     *
     * @throws JSchException JSchException
     */
    public void connect() throws JSchException {
        session = jsch.getSession(hostMachine.getUsername(), hostMachine.getHostname(), DEFAULT_PORT);
        session.setPassword(hostMachine.getPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }

    /**
     * Execute command as superuser on remote machine {@link HostMachine}.
     *
     * @param command the command to execute
     * @return the command output
     * @throws JSchException JSchException
     * @throws IOException   Signals that an I/O exception has occurred.
     */
    public String executeAsSU(String command) throws JSchException, IOException {
        StringBuilder buffer = new StringBuilder();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setErrStream(System.err);
        channel.setPty(true);
        channel.setCommand("sudo -S -p '' " + command);

        OutputStream os = channel.getOutputStream();
        BufferedReader is = new BufferedReader(new InputStreamReader(channel.getInputStream()));

        channel.connect();

        os.write((hostMachine.getPassword() + System.getProperty("line.separator")).getBytes());
        os.flush();

        String line;
        while ((line = is.readLine()) != null) {
            buffer.append(line).append('\n');
        }

        channel.disconnect();
        return buffer.toString();
    }

    /**
     * Execute command on remote machine {@link HostMachine}.
     *
     * @param command the command to execute
     * @return the command output
     * @throws JSchException JSchException
     * @throws IOException   Signals that an I/O exception has occurred.
     */
    public String execute(String command) throws JSchException, IOException {
        StringBuilder buffer = new StringBuilder();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        BufferedReader is = new BufferedReader(new InputStreamReader(channel.getInputStream()));
        channel.connect();

        String line;
        while ((line = is.readLine()) != null) {
            buffer.append(line).append('\n');
        }

        channel.disconnect();
        return buffer.toString().trim();
    }

    /**
     * Check is file exist and have the same file size
     *
     * @param folderPath path to folder
     * @param file       file
     * @return {@code true} if filename and file size is same
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    public boolean isFileExist(String folderPath, File file, String filename) throws JSchException, SftpException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        if (!isFolderExist(folderPath))
            return false;

        Vector folderFiles = channel.ls(folderPath);

        for (Object folderFile : folderFiles) {
            ChannelSftp.LsEntry element = (ChannelSftp.LsEntry) folderFile;
            if (element.getAttrs().getSize() == file.length() && element.getFilename().equals(filename))
                return true;
        }
        return false;
    }

    /**
     * Check is folder exist
     *
     * @param folderPath which folder in root directory must exist
     * @return {@code true} is folder exist
     * @throws JSchException JSchException
     */
    private boolean isFolderExist(String folderPath) throws JSchException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        try {
            return channel.lstat(folderPath).isDir();
        } catch (Exception e) {
            return false;
        } finally {
            channel.disconnect();
        }
    }

    /**
     * Get path to folder in remote.
     * If folder is not exist, create it.
     *
     * @return {@link String} path to folder
     */
    public String getPathToFolder(String folder) throws SftpException, JSchException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        String homePath = channel.getHome();
        String path = homePath + hostMachine.getFileSeparator() + folder;

        if (!isFolderExist(path))
            channel.mkdir(path);

        channel.disconnect();
        return path;
    }

    private void cdDir(ChannelSftp channel, String folderPath) throws SftpException, JSchException {
        if (!isFolderExist(folderPath))
            channel.mkdir(folderPath);
        channel.cd(folderPath);
    }

    /**
     * Upload file in root directory folder and set filename.
     *
     * @param folderPath folder in root directory, where file must be uploaded
     * @param file       {@link File}to upload
     * @param filename   how uploaded file must be named
     * @throws JSchException         JSchException
     * @throws SftpException         SftpException
     * @throws FileNotFoundException FileNotFoundException
     */
    public void uploadFile(String folderPath, File file, String filename) throws
            JSchException, SftpException, FileNotFoundException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        cdDir(channel, folderPath);

        channel.put(new FileInputStream(file), filename, ChannelSftp.OVERWRITE);
        channel.disconnect();
    }

    /**
     * Download file from remote machine to target directory
     *
     * @param targetDir        to which directory need to download file
     * @param sourceFolderPath from which folder need to download file
     * @param sourceFileName   which file need
     * @return {@link File} downloaded file in target directory
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     * @throws IOException   IOException
     */
    public File downloadFile(File targetDir, String sourceFolderPath, String sourceFileName) throws
            JSchException, SftpException, IOException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        File targetFile = new File(targetDir, sourceFileName);
        if (targetFile.exists() && !targetFile.delete()) {
            throw new RuntimeException("File " + targetFile.getAbsolutePath() + " was not deleted");
        }

        if (targetFile.createNewFile()) {
            cdDir(channel, sourceFolderPath);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            channel.get(sourceFileName, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }

        return targetFile;
    }

    /**
     * Remove file/directory
     *
     * @param folderPath folder path, which need to remove
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    public void removeFile(String folderPath, String filename) throws JSchException, SftpException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        String path = folderPath + hostMachine.getFileSeparator() + filename;
        channel.rm(path);
        channel.disconnect();
    }

    /**
     * Disconnect SSH connection
     */
    public void disconnect() {
        session.disconnect();
    }

}
