package com.frank.solidity.model;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.4.
 */
@SuppressWarnings("rawtypes")
public class ImageWarrant extends Contract {
    public static final String BINARY = "60806040526040516108e53803806108e5833981016040819052610022916100f4565b815161003590600390602085019061005b565b50600155506002805460ff19169055600080546001600160a01b03191633179055610217565b828054610067906101c6565b90600052602060002090601f01602090048101928261008957600085556100cf565b82601f106100a257805160ff19168380011785556100cf565b828001600101855582156100cf579182015b828111156100cf5782518255916020019190600101906100b4565b506100db9291506100df565b5090565b5b808211156100db57600081556001016100e0565b60008060408385031215610106578182fd5b82516001600160401b038082111561011c578384fd5b818501915085601f83011261012f578384fd5b81518181111561014157610141610201565b604051601f8201601f19908116603f0116810190838211818310171561016957610169610201565b81604052828152602093508884848701011115610184578687fd5b8691505b828210156101a55784820184015181830185015290830190610188565b828211156101b557868484830101525b969092015195979596505050505050565b600181811c908216806101da57607f821691505b602082108114156101fb57634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fd5b6106bf806102266000396000f3fe6080604052600436106100865760003560e01c80638da5cb5b116100595780638da5cb5b14610129578063a035b1fe14610149578063a1a99c1a1461016d578063a6f2ae3a1461018f578063bbe6b7011461019757600080fd5b80631b55b63d1461008b57806334c19e40146100c05780636937259b146100e2578063893d20e8146100fc575b600080fd5b34801561009757600080fd5b506100ab6100a6366004610553565b6101b7565b60405190151581526020015b60405180910390f35b3480156100cc57600080fd5b506100e06100db366004610581565b610251565b005b3480156100ee57600080fd5b506002546100ab9060ff1681565b34801561010857600080fd5b506101116102ce565b6040516001600160a01b0390911681526020016100b7565b34801561013557600080fd5b50600054610111906001600160a01b031681565b34801561015557600080fd5b5061015f60015481565b6040519081526020016100b7565b34801561017957600080fd5b50610182610309565b6040516100b791906105b9565b6100ab6103c6565b3480156101a357600080fd5b506100e06101b23660046105a1565b6104ee565b600080546001600160a01b031633146101eb5760405162461bcd60e51b81526004016101e29061060c565b60405180910390fd5b600080546001600160a01b0319166001600160a01b0384161781556002805460ff19169055604080518281523360208201527fcd6fc38b7295b447baad9395bac887aae82ca26a0575844f4228faef21a32581910160405180910390a150600192915050565b6000546001600160a01b0316331461027b5760405162461bcd60e51b81526004016101e29061060c565b6002805460ff19168215159081179091556040805133815260ff909216151560208301527f8cdccecb36534579ae4906c2f25361abd2f33d7a2d2aa61fe054c9ba0ab1424991015b60405180910390a150565b600080546001600160a01b031633146102f95760405162461bcd60e51b81526004016101e29061060c565b506000546001600160a01b031690565b6000546060906001600160a01b031633146103365760405162461bcd60e51b81526004016101e29061060c565b600380546103439061064e565b80601f016020809104026020016040519081016040528092919081815260200182805461036f9061064e565b80156103bc5780601f10610391576101008083540402835291602001916103bc565b820191906000526020600020905b81548152906001019060200180831161039f57829003601f168201915b5050505050905090565b60025460009060ff166104125760405162461bcd60e51b815260206004820152601460248201527324b6b0b3b2b9903737ba103337b91039b0b6329760611b60448201526064016101e2565b60015434101561043b57600154604051634e12c1bb60e01b81526004016101e291815260200190565b600080546040516001600160a01b039091169182913480156108fc0292909190818181858888f19350505050158015610478573d6000803e3d6000fd5b50600080546001600160a01b031916339081179091553460018190556002805460ff19169055604080516001600160a01b038516815260208101939093528201527f2f2534366cc211957885174ac7a50ee96da3f272d5cfdb8060ff1087214d17c29060600160405180910390a1600191505090565b6000546001600160a01b031633146105185760405162461bcd60e51b81526004016101e29061060c565b600181905560408051338152602081018390527f966ca76fd127c0d5392b3f47fa2f4e4d147a20148ddbbe5bdffc3fed58de94a091016102c3565b600060208284031215610564578081fd5b81356001600160a01b038116811461057a578182fd5b9392505050565b600060208284031215610592578081fd5b8135801515811461057a578182fd5b6000602082840312156105b2578081fd5b5035919050565b6000602080835283518082850152825b818110156105e5578581018301518582016040015282016105c9565b818111156105f65783604083870101525b50601f01601f1916929092016040019392505050565b60208082526022908201527f4f6e6c79206f776e65722063616e2063616c6c20746869732066756e6374696f604082015261371760f11b606082015260800190565b600181811c9082168061066257607f821691505b6020821081141561068357634e487b7160e01b600052602260045260246000fd5b5091905056fea264697066735822122066ec8f5457e70de6a2e796acc69edf6cb0a4c9a99e0983d1ce4ca4196c97285964736f6c63430008040033";

    public static final String FUNC_BUY = "buy";

    public static final String FUNC_GETIMAGEPATH = "getImagePath";

    public static final String FUNC_GETOWNER = "getOwner";

    public static final String FUNC_ISSALABILITY = "isSalability";

    public static final String FUNC_MODIFYPRICE = "modifyPrice";

    public static final String FUNC_MODIFYSALABILITY = "modifySalability";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_SUBGIFT = "subgift";

    public static final Event BUYPRICE_EVENT = new Event("BuyPrice", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANGEPRICE_EVENT = new Event("ChangePrice", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANGESALABILITY_EVENT = new Event("ChangeSalability", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event GIVE_EVENT = new Event("Give", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected ImageWarrant(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ImageWarrant(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ImageWarrant(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ImageWarrant(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BuyPriceEventResponse> getBuyPriceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BUYPRICE_EVENT, transactionReceipt);
        ArrayList<BuyPriceEventResponse> responses = new ArrayList<BuyPriceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BuyPriceEventResponse typedResponse = new BuyPriceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BuyPriceEventResponse> buyPriceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BuyPriceEventResponse>() {
            @Override
            public BuyPriceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BUYPRICE_EVENT, log);
                BuyPriceEventResponse typedResponse = new BuyPriceEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BuyPriceEventResponse> buyPriceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BUYPRICE_EVENT));
        return buyPriceEventFlowable(filter);
    }

    public static List<ChangePriceEventResponse> getChangePriceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CHANGEPRICE_EVENT, transactionReceipt);
        ArrayList<ChangePriceEventResponse> responses = new ArrayList<ChangePriceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChangePriceEventResponse typedResponse = new ChangePriceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChangePriceEventResponse> changePriceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChangePriceEventResponse>() {
            @Override
            public ChangePriceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGEPRICE_EVENT, log);
                ChangePriceEventResponse typedResponse = new ChangePriceEventResponse();
                typedResponse.log = log;
                typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChangePriceEventResponse> changePriceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGEPRICE_EVENT));
        return changePriceEventFlowable(filter);
    }

    public static List<ChangeSalabilityEventResponse> getChangeSalabilityEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CHANGESALABILITY_EVENT, transactionReceipt);
        ArrayList<ChangeSalabilityEventResponse> responses = new ArrayList<ChangeSalabilityEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChangeSalabilityEventResponse typedResponse = new ChangeSalabilityEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.isSalability = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChangeSalabilityEventResponse> changeSalabilityEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChangeSalabilityEventResponse>() {
            @Override
            public ChangeSalabilityEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGESALABILITY_EVENT, log);
                ChangeSalabilityEventResponse typedResponse = new ChangeSalabilityEventResponse();
                typedResponse.log = log;
                typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.isSalability = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChangeSalabilityEventResponse> changeSalabilityEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGESALABILITY_EVENT));
        return changeSalabilityEventFlowable(filter);
    }

    public static List<GiveEventResponse> getGiveEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(GIVE_EVENT, transactionReceipt);
        ArrayList<GiveEventResponse> responses = new ArrayList<GiveEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GiveEventResponse typedResponse = new GiveEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GiveEventResponse> giveEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, GiveEventResponse>() {
            @Override
            public GiveEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GIVE_EVENT, log);
                GiveEventResponse typedResponse = new GiveEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GiveEventResponse> giveEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GIVE_EVENT));
        return giveEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> buy(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> getImagePath() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETIMAGEPATH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isSalability() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSALABILITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyPrice(BigInteger imagePrice) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(imagePrice)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> modifySalability(Boolean _isSalability) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYSALABILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_isSalability)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> price() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> subgift(String to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBGIFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ImageWarrant load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ImageWarrant(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ImageWarrant load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ImageWarrant(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ImageWarrant load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ImageWarrant(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ImageWarrant load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ImageWarrant(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ImageWarrant> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String _imagePath, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_imagePath), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(ImageWarrant.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<ImageWarrant> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String _imagePath, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_imagePath), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(ImageWarrant.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<ImageWarrant> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _imagePath, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_imagePath), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(ImageWarrant.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<ImageWarrant> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _imagePath, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_imagePath), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(ImageWarrant.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static class BuyPriceEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger price;
    }

    public static class ChangePriceEventResponse extends BaseEventResponse {
        public String bidder;

        public BigInteger price;
    }

    public static class ChangeSalabilityEventResponse extends BaseEventResponse {
        public String bidder;

        public Boolean isSalability;
    }

    public static class GiveEventResponse extends BaseEventResponse {
        public String from;

        public String to;
    }
}
