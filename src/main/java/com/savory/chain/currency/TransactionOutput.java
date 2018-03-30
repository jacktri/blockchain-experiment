package com.savory.chain.currency;

import java.security.PublicKey;
import java.util.Objects;

import com.savory.chain.StringUtil;

public class TransactionOutput {

    private String id;
    private PublicKey recipient; //also known as the new owner of these coins.
    private float value;
    private String parentTransactionId; //the id of the transaction this output was created in

    public TransactionOutput(PublicKey recipient, float value, String parentTransactionId) {
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient)+Float.toString(value)+parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == recipient);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public void setRecipient(PublicKey recipient) {
        this.recipient = recipient;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionOutput)) {
            return false;
        }
        TransactionOutput that = (TransactionOutput) o;
        return Float.compare(that.value, value) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(recipient, that.recipient) &&
            Objects.equals(parentTransactionId, that.parentTransactionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, recipient, value, parentTransactionId);
    }
}
