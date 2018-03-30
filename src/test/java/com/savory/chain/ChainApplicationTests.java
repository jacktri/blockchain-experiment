package com.savory.chain;

import static com.savory.chain.BlockChainValidator.isChainValid;
import static com.savory.chain.StringUtil.jsonToBlocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChainApplicationTests {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void generateBlockChain() throws IOException {
        List<Block> blockChain = new ArrayList<>();
        int difficulty = 5;
        blockChain.add(new Block("Hi im the first block", "0"));
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("Yo im the second block",blockChain.get(blockChain.size()-1).getHash()));
        blockChain.get(1).mineBlock(difficulty);

        blockChain.add(new Block("Hey im the third block",blockChain.get(blockChain.size()-1).getHash()));
        blockChain.get(2).mineBlock(difficulty);

        String blockchainJson = objectMapper.writeValueAsString(blockChain);
        assertTrue(isChainValid(difficulty, blockChain));
        List<Block> result = jsonToBlocks(blockchainJson);
        assertEquals(blockChain, result);
    }

    @Test
    public void isBlockChainValidTest() throws IOException {
        File jsonFile = new ClassPathResource("test.json").getFile();
        int difficulty = 5;
        List<Block> blockChain = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Block.class));
        assertTrue(isChainValid(difficulty, blockChain));
    }

    @Test
    public void invalidBlockTest() throws IOException {
        File jsonFile = new ClassPathResource("invalid.json").getFile();
        int difficulty = 5;
        List<Block> blockChain = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Block.class));
        assertFalse(isChainValid(difficulty, blockChain));
    }

}
