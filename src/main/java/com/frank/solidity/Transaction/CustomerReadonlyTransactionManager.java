package com.frank.solidity.Transaction;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.exceptions.ContractCallException;

import java.io.IOException;

public class CustomerReadonlyTransactionManager extends ReadonlyTransactionManager {
    private final Web3j web3j;
    private String fromAddress;

    public static final String REVERT_ERR_STR =
            "Contract Call has been Error with the reason: '%s'.";

    public CustomerReadonlyTransactionManager(Web3j web3j, String fromAddress) {
        super(web3j, fromAddress);
        this.web3j = web3j;
        this.fromAddress = fromAddress;
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        EthCall ethCall =
                this.web3j.ethCall(
                                Transaction.createEthCallTransaction(fromAddress, to, data),
                                defaultBlockParameter)
                        .send();

        assertCallNotError(ethCall);
        assertCallNotReverted(ethCall);
        return ethCall.getValue();
    }

    static void assertCallNotError(EthCall ethCall) {
        if (ethCall.hasError()) {
            throw new ContractCallException(
                    String.format(REVERT_ERR_STR, ethCall.getError().getMessage()));
        }
    }

    static void assertCallNotReverted(EthCall ethCall) {
        if (ethCall.isReverted()) {
            throw new ContractCallException(
                    String.format(REVERT_ERR_STR, ethCall.getRevertReason()));
        }
    }
}
