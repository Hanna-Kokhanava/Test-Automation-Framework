package com.linkedin.automation.services.ui.handlers;


import com.linkedin.automation.page_elements.block.IMobileBlock;
import com.linkedin.automation.services.ui.PageElementProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public abstract class AbstractBlockHandler<BlockType extends IMobileBlock> {

    private PageElementProvider<BlockType> blockProvider;

    public AbstractBlockHandler() {
        this((BlockType) null);
    }

    public AbstractBlockHandler(BlockType block) {
        setBlock(block);
    }

    public AbstractBlockHandler(PageElementProvider<BlockType> blockProvider) {
        setBlock(blockProvider);
    }

    @Nullable
    public BlockType getBlock() { //TODO maybe replace by getCheckedBlock
        return Objects.isNull(blockProvider) ? null : blockProvider.getPageElement();
    }

    public void setBlock(@Nullable BlockType block) {
        if (null != blockProvider && block == blockProvider.getPageElement()) {
            return;
        }
        blockProvider = new PageElementProvider<BlockType>() {
            BlockType element = block;

            @Override
            public BlockType getPageElement() {
                return element;
            }
        };

    }

    public void setBlock(@Nullable PageElementProvider<BlockType> blockProvider) {
        this.blockProvider = blockProvider;
    }

    @Nonnull
    public BlockType getCheckedBlock() {
        return Objects.requireNonNull(getBlock(), "Not defined Block instance");
    }

}
