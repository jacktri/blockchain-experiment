package com.savory.chain.currency;

import java.util.Objects;

public class TransactionInput {
    private String transactionOutputId;
    private TransactionOutput transactionOutput; //Contains the Unspent transaction output

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public String getTransactionOutputId() {
        return transactionOutputId;
    }

    public void setTransactionOutputId(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public TransactionOutput getTransactionOutput() {
        return transactionOutput;
    }

    public void setTransactionOutput(TransactionOutput transactionOutput) {
        this.transactionOutput = transactionOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionInput)) {
            return false;
        }
        TransactionInput that = (TransactionInput) o;
        return Objects.equals(transactionOutputId, that.transactionOutputId) &&
            Objects.equals(transactionOutput, that.transactionOutput);
    }

    @Override
    public int hashCode() {

        return Objects.hash(transactionOutputId, transactionOutput);
    }
}
