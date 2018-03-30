package com.savory.chain;

import static com.savory.chain.BlockChainUtil.addBlock;
import static com.savory.chain.BlockChainUtil.isChainValid;
import static com.savory.chain.ChainApplication.GENESIS_TRANSACTION;
import static com.savory.chain.ChainApplication.UTXOS;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.savory.chain.currency.Transaction;
import com.savory.chain.currency.TransactionOutput;
import com.savory.chain.currency.Wallet;

public class ChainApplicationTests {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static Block genesis = new Block("0");
    private static Wallet walletA = new Wallet();
    private static Wallet walletB = new Wallet();

    @BeforeAll
    public static void setUp() {
        Wallet coinbase = new Wallet();
        GENESIS_TRANSACTION = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f, null);
        GENESIS_TRANSACTION.generateSignature(coinbase.getPrivateKey());  //manually sign the genesis transaction
        GENESIS_TRANSACTION.setTransactionId("0"); //manually set the transaction id
        GENESIS_TRANSACTION.getOutputs().add(new TransactionOutput(GENESIS_TRANSACTION.getReciepient(),
            GENESIS_TRANSACTION.getValue(), GENESIS_TRANSACTION.getTransactionId())); //manually add the Transactions Output

        UTXOS.put(GENESIS_TRANSACTION.getOutputs().get(0).getId(), GENESIS_TRANSACTION.getOutputs().get(0)); //its important to store our first transaction in the UTXOs list.

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(GENESIS_TRANSACTION);
        addBlock(genesis);
    }

    @Test
    public void transactionTest() {
        Security.addProvider(new BouncyCastleProvider());
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();

        Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(), 5, null);
        transaction.generateSignature(walletA.getPrivateKey());
        assertTrue(transaction.verifiySignature());
    }

    @Test
    public void transactionTestWithGenesisTransaction() {
        Block block1 = new Block(genesis.getHash());
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
        block1.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 40f));
        addBlock(block1);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

        Block block2 = new Block(block1.getHash());
        System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
        block2.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 1000f));
        addBlock(block2);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

        Block block3 = new Block(block2.getHash());
        System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
        block3.addTransaction(walletB.sendFunds(walletA.getPublicKey(), 20f));
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

        assertTrue(isChainValid());
    }
}
