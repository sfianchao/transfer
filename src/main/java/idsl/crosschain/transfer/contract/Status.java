package idsl.crosschain.transfer.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Status extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040518060400160405280600481526020017f696e6974000000000000000000000000000000000000000000000000000000008152506000908051906020019061005c929190610062565b50610166565b82805461006e90610105565b90600052602060002090601f01602090048101928261009057600085556100d7565b82601f106100a957805160ff19168380011785556100d7565b828001600101855582156100d7579182015b828111156100d65782518255916020019190600101906100bb565b5b5090506100e491906100e8565b5090565b5b808211156101015760008160009055506001016100e9565b5090565b6000600282049050600182168061011d57607f821691505b6020821081141561013157610130610137565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b610579806101756000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063200d2ed2146100515780636ebfd6df1461006f57806396816bd714610079578063a3dd261914610083575b600080fd5b6100596100a1565b6040516100669190610429565b60405180910390f35b61007761012f565b005b6100816101b5565b005b61008b61023b565b6040516100989190610429565b60405180910390f35b600080546100ae906104d1565b80601f01602080910402602001604051908101604052809291908181526020018280546100da906104d1565b80156101275780601f106100fc57610100808354040283529160200191610127565b820191906000526020600020905b81548152906001019060200180831161010a57829003601f168201915b505050505081565b6040518060400160405280600681526020017f636f6d6d697400000000000000000000000000000000000000000000000000008152506000908051906020019061017a9291906102cd565b507f7d2211d63bbbbeea171c05f703e2748272ed0f62af7970a1f95da2fbeec3402360006040516101ab919061044b565b60405180910390a1565b6040518060400160405280600781526020017f7072657061726500000000000000000000000000000000000000000000000000815250600090805190602001906102009291906102cd565b507f7d2211d63bbbbeea171c05f703e2748272ed0f62af7970a1f95da2fbeec340236000604051610231919061044b565b60405180910390a1565b60606000805461024a906104d1565b80601f0160208091040260200160405190810160405280929190818152602001828054610276906104d1565b80156102c35780601f10610298576101008083540402835291602001916102c3565b820191906000526020600020905b8154815290600101906020018083116102a657829003601f168201915b5050505050905090565b8280546102d9906104d1565b90600052602060002090601f0160209004810192826102fb5760008555610342565b82601f1061031457805160ff1916838001178555610342565b82800160010185558215610342579182015b82811115610341578251825591602001919060010190610326565b5b50905061034f9190610353565b5090565b5b8082111561036c576000816000905550600101610354565b5090565b600061037b82610482565b610385818561048d565b935061039581856020860161049e565b61039e81610532565b840191505092915050565b600081546103b6816104d1565b6103c0818661048d565b945060018216600081146103db57600181146103ed57610420565b60ff1983168652602086019350610420565b6103f68561046d565b60005b83811015610418578154818901526001820191506020810190506103f9565b808801955050505b50505092915050565b600060208201905081810360008301526104438184610370565b905092915050565b6000602082019050818103600083015261046581846103a9565b905092915050565b60008190508160005260206000209050919050565b600081519050919050565b600082825260208201905092915050565b60005b838110156104bc5780820151818401526020810190506104a1565b838111156104cb576000848401525b50505050565b600060028204905060018216806104e957607f821691505b602082108114156104fd576104fc610503565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000601f19601f830116905091905056fea2646970667358221220eea3e52701964d45f760573472fa81e70d255c53d9de8859c89a45747e0bcf6a64736f6c63430008070033";

    public static final String FUNC_GETCURRENTSTATUS = "getCurrentStatus";

    public static final String FUNC_SETSTATUSTOCOMMIT = "setStatusToCommit";

    public static final String FUNC_SETSTATUSTOPREPARE = "setStatusToPrepare";

    public static final String FUNC_STATUS = "status";

    public static final Event SETSTATUS_EVENT = new Event("setStatus",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
            }));
    ;

    @Deprecated
    protected Status(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Status(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Status(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Status(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<SetStatusEventResponse> getSetStatusEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SETSTATUS_EVENT, transactionReceipt);
        ArrayList<SetStatusEventResponse> responses = new ArrayList<SetStatusEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SetStatusEventResponse typedResponse = new SetStatusEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.status = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SetStatusEventResponse> setStatusEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SetStatusEventResponse>() {
            @Override
            public SetStatusEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SETSTATUS_EVENT, log);
                SetStatusEventResponse typedResponse = new SetStatusEventResponse();
                typedResponse.log = log;
                typedResponse.status = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SetStatusEventResponse> setStatusEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETSTATUS_EVENT));
        return setStatusEventFlowable(filter);
    }

    public RemoteFunctionCall<String> getCurrentStatus() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTSTATUS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setStatusToCommit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTATUSTOCOMMIT,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStatusToPrepare() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTATUSTOPREPARE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> status() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STATUS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Status load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Status(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Status load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Status(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Status load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Status(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Status load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Status(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Status> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Status.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Status> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Status.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Status> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Status.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Status> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Status.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SetStatusEventResponse extends BaseEventResponse {
        public String status;
    }
}
