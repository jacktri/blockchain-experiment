package com.savory.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.savory.chain.currency.Transaction;
import com.savory.chain.currency.TransactionOutput;

public class ChainApplication {

    public static int DIFFICULTY = 5;
    public static List<Block> BLOCK_CHAIN = new ArrayList<>();
    public static Transaction GENESIS_TRANSACTION = new Transaction();
    public static Map<String,TransactionOutput> UTXOS = new HashMap<>();

    public static void main(String[] args) {
        // Do nothing because no functionality yet
    }
}
