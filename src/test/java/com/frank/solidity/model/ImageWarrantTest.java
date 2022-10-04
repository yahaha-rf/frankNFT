package com.frank.solidity.model;


import com.frank.solidity.Transaction.CustomerReadonlyTransactionManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static com.frank.solidity.model.ImageWarrant.BUYPRICE_EVENT;
import static com.frank.solidity.model.ImageWarrant.CHANGEPRICE_EVENT;
import static org.web3j.tx.Contract.staticExtractEventParameters;

@Slf4j
public class ImageWarrantTest {

    // 测试链在这 https://dashboard.alchemy.com/apps/gcer1shjomiv30if
    private static Web3j web3j = Web3j.build(new HttpService("https://eth-goerli.g.alchemy.com/v2/Eh6N1fEMmgbaZaJBi_RGHrp2ZElQN3S0"));

    private static String imageWarrantcontractAddress = "0x5dba094af6983234d48b0941a0cbbc687b16cbef";

    // 账户地址
    private static String fromAddress1 = "0xf1d5319baEc621c90D20420dc5D55E5be4B623CA";

    private static String fromAddress2 = "0x823a82F6800f808a9972792943594d0862FfAA97";

    private static Credentials getCredentials() {
        // 私钥
        return Credentials.create("58f7bce66f252a2192456ec97c292770aff1a5abca1279187bc7c6fdf625c1ec");
    }

    private static Credentials getCredentials2() {
        // 私钥
        return Credentials.create("33f2960ae209d2b60374685f8904a73df33721bb16aa843d2a4444feaaa58268");
    }



    @SneakyThrows
    private static ContractGasProvider getGasProvider() {
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        BigInteger gasLimit = BigInteger.valueOf(1_000_000_000_000_000_000L);

        return new StaticGasProvider(gasPrice, gasLimit);
    }

    // 查询账户余额
    @SneakyThrows
    @Test
    public void getBalance() {
        BigInteger balance = web3j.ethGetBalance(fromAddress1, DefaultBlockParameterName.LATEST).send().getBalance();
        BigDecimal divide = new BigDecimal(balance).divide(Convert.Unit.ETHER.getWeiFactor(), 6, RoundingMode.HALF_DOWN);
        log.info("账户1余额为：{} ETH",divide);

        BigInteger balance2 = web3j.ethGetBalance(fromAddress2, DefaultBlockParameterName.LATEST).send().getBalance();
        BigDecimal divide2 = new BigDecimal(balance2).divide(Convert.Unit.ETHER.getWeiFactor(), 6, RoundingMode.HALF_DOWN);
        log.info("账户2余额为：{} ETH",divide2);
    }

    // 生成一个图像凭证
    // 类里面的imageWarrantcontractAddress手动改成日志输出的
    @SneakyThrows
    @Test
    public void deployImageWarrant() {
        ImageWarrant imageWarrant = ImageWarrant.deploy(web3j, getCredentials2(), new DefaultGasProvider(), new BigInteger("0"), "D:\\头像2.png", new BigInteger("0")).send();
        imageWarrantcontractAddress = imageWarrant.getContractAddress();
        // imageWarrantcontractAddress需要保存
        log.info("生成ImageWarrant的contractAddress为：" + imageWarrantcontractAddress);
        web3j.shutdown();
    }

    // 账户1查询图片信息
    @SneakyThrows
    @Test
    public void getInfo() {
        TransactionManager transactionManager = new CustomerReadonlyTransactionManager(web3j, fromAddress1);
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, getGasProvider());

        if (imageWarrant.isValid()) {
            Boolean isSalability = imageWarrant.isSalability().send();
            log.info("图片是否可出售：" + isSalability);

            BigInteger price = imageWarrant.price().send();
            log.info("图片价格：" + price);

            String imagePath = imageWarrant.getImagePath().send();
            log.info("图片路径：" + imagePath);
        }
        web3j.shutdown();
    }

    // 账户2查询图片信息
    @SneakyThrows
    @Test
    public void getInfo2() {
        TransactionManager transactionManager = new CustomerReadonlyTransactionManager(web3j, fromAddress2);
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, getGasProvider());

        if (imageWarrant.isValid()) {
            Boolean isSalability = imageWarrant.isSalability().send();
            log.info("图片是否可出售：" + isSalability);

            BigInteger price = imageWarrant.price().send();
            log.info("图片价格：" + price);

            String imagePath = imageWarrant.getImagePath().send();
            log.info("图片路径：" + imagePath);
        }
        web3j.shutdown();
    }

    // 修改图片是否可购买
    @SneakyThrows
    @Test
    public void updateIsSalability() {
        TransactionManager transactionManager = new RawTransactionManager(web3j, getCredentials2()) {
        };
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, new DefaultGasProvider());

        if (imageWarrant.isValid()) {
            TransactionReceipt send = imageWarrant.modifySalability(true).send();
            System.out.println(send.toString());

            // 上链成功也无法确保会留在主链上，ETH需要等待6个区块确认， 此步骤可放到定时任务去扫描
            // 等待6个区块确认 避免双花问题
            Assertions.assertEquals("0x1", send.getStatus());

        }
        web3j.shutdown();
    }

    // 修改图片价格
    @SneakyThrows
    @Test
    public void updatePrice() {
        TransactionManager transactionManager = new RawTransactionManager(web3j, getCredentials2()) {
        };
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, new DefaultGasProvider());

        if (imageWarrant.isValid()) {
            TransactionReceipt send = imageWarrant.modifyPrice(new BigInteger("4")).send();
            log.info(send.toString());

            // 上链成功也无法确保会留在主链上，ETH需要等待6个区块确认， 此步骤可放到定时任务去扫描
            // 等待6个区块确认 避免双花问题
            Assertions.assertEquals("0x1", send.getStatus());

        }
        web3j.shutdown();
    }

    // 获取修改价格日志
    @SneakyThrows
    @Test
    public void getUpdatePriceLog() {

        ImageWarrant traceContract = ImageWarrant.load(imageWarrantcontractAddress, web3j, new ReadonlyTransactionManager(web3j,fromAddress1), getGasProvider());
        EthFilter ethFilter = ethFilter(traceContract);
        ethFilter.addSingleTopic(EventEncoder.encode(CHANGEPRICE_EVENT));
        List<EventValuesWithLog> collect = web3j.ethGetLogs(ethFilter).send().getLogs()
                .stream()
                .map(logResult -> (EthLog.LogObject) logResult)
                .map(log -> {
                    EventValues eventValues = staticExtractEventParameters(CHANGEPRICE_EVENT, log);
                    return new EventValuesWithLog(eventValues, log);
                })
                .collect(Collectors.toList());

        for (EventValuesWithLog eventValues : collect) {
            ImageWarrant.ChangePriceEventResponse typedResponse = new ImageWarrant.ChangePriceEventResponse();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();

            log.info("修改人:{}",typedResponse.bidder);
            log.info("price:{}",typedResponse.price);
            log.info("交易哈希:{}",eventValues.getLog().getTransactionHash());
            log.info("------------------------");
        }

    }

    // 账户2修改图片价格
    @SneakyThrows
    @Test
    public void updatePrice2() {
        TransactionManager transactionManager = new RawTransactionManager(web3j, getCredentials2()) {
        };
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, getGasProvider());

        if (imageWarrant.isValid()) {
            TransactionReceipt send = imageWarrant.modifyPrice(new BigInteger("1")).send();
            log.info(send.toString());

            // 上链成功也无法确保会留在主链上，ETH需要等待6个区块确认， 此步骤可放到定时任务去扫描
            // 等待6个区块确认 避免双花问题
            Assertions.assertEquals("0x1", send.getStatus());
        }
        web3j.shutdown();
    }

    // 账户1转赠图片给账户2
    @SneakyThrows
    @Test
    public void aTob() {
        TransactionManager transactionManager = new RawTransactionManager(web3j, getCredentials()) {
        };
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, getGasProvider());

        if (imageWarrant.isValid()) {
            TransactionReceipt send = imageWarrant.subgift(fromAddress2).send();
            log.info(send.toString());
        }
        web3j.shutdown();
    }

    // 账户1购买图片
    @SneakyThrows
    @Test
    public void buy() {
        TransactionManager transactionManager = new RawTransactionManager(web3j, getCredentials()) {
        };
        ImageWarrant imageWarrant = ImageWarrant.load(imageWarrantcontractAddress, web3j, transactionManager, new DefaultGasProvider());

        if (imageWarrant.isValid()) {
            TransactionReceipt send = imageWarrant.buy(new BigInteger("4")).send();
            log.info(send.toString());

            // 上链成功也无法确保会留在主链上，ETH需要等待6个区块确认， 此步骤可放到定时任务去扫描
            // 等待6个区块确认 避免双花问题
            Assertions.assertEquals("0x1", send.getStatus());
        }
        web3j.shutdown();
    }

    // 获取购买图片日志
    // 任意用户都能看到日志
    @SneakyThrows
    @Test
    public void getBuyLog() {

        ImageWarrant traceContract = ImageWarrant.load(imageWarrantcontractAddress, web3j, new ReadonlyTransactionManager(web3j,fromAddress1), getGasProvider());
        EthFilter ethFilter = ethFilter(traceContract);
        ethFilter.addSingleTopic(EventEncoder.encode(BUYPRICE_EVENT));
        List<EventValuesWithLog> collect = web3j.ethGetLogs(ethFilter).send().getLogs()
                .stream()
                .map(logResult -> (EthLog.LogObject) logResult)
                .map(log -> {
                    EventValues eventValues = staticExtractEventParameters(BUYPRICE_EVENT, log);
                    return new EventValuesWithLog(eventValues, log);
                })
                .collect(Collectors.toList());

        for (EventValuesWithLog eventValues : collect) {
            ImageWarrant.BuyPriceEventResponse typedResponse = new ImageWarrant.BuyPriceEventResponse();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();

            log.info("谁买了:{}",typedResponse.from);
            log.info("谁卖了:{}",typedResponse.to);
            log.info("交易哈希:{}",eventValues.getLog().getTransactionHash());
            log.info("------------------------");
        }

    }


    public static EthFilter ethFilter(ImageWarrant trace) {
        return new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                trace.getContractAddress());
    }

    public static class EventValuesWithLog {
        private final EventValues eventValues;
        private final Log log;

        private EventValuesWithLog(EventValues eventValues, Log log) {
            this.eventValues = eventValues;
            this.log = log;
        }

        public List<Type> getIndexedValues() {
            return eventValues.getIndexedValues();
        }

        public List<Type> getNonIndexedValues() {
            return eventValues.getNonIndexedValues();
        }

        public Log getLog() {
            return log;
        }
    }
}
