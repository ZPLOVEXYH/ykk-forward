package cn.samples.datareceiver.codec;

import cn.samples.datareceiver.model.ContaPicture;
import cn.samples.datareceiver.model.DataPackage;
import cn.samples.datareceiver.model.Stopwatch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")
public class DataDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(DataDecoder.class);

    //接收报文大小限制 小于等于0时不限制
    private final int BUF_MAX = 20 * 1048576; //20M

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int cnt = in.refCnt();
        log.info("打印出来的计数为：{}", cnt);
//        if (cnt > 0) {

            DataPackage dp = decode(in);
            if (null != dp) {
                out.add(dp);
            }
//        }


    }

    public DataPackage decode(ByteBuf in) {
        if (in.readableBytes() < 8) {
            return null;
        }
        // check head flag(0x89 0x4B 0x5C 0xE2)
        int head = in.readIntLE();// head=-1991549726(溢出值)0x89 4B 5C E2
        if (head != -1991549726) {
//			in.resetReaderIndex();
            in.release();
            log.info("打印的包头信息：{}", head);
            throw new CorruptedFrameException("Invalid head package.");
        }
        int dataLength = in.readIntLE();
        if (dataLength > BUF_MAX) {
            in.release();
            throw new TooLongFrameException("The package too lager.");
        }
        in.resetReaderIndex();
        // Wait until the whole data is available.
        if (in.readableBytes() < dataLength) {
            return null;
        }

        Stopwatch timer = Stopwatch.createStarted(); // 添加计时

        logger.debug("dataLength = {}", dataLength);
        // check tail flag(0xff 0xff)
        in.readerIndex(dataLength - 2);
        if (in.readByte() == 0xff && in.readByte() == 0xff) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid tail package.");
        }
        // get fully bytes
        byte[] alldata = new byte[dataLength];
        in.getBytes(0, alldata);
        logger.debug("alldata.length = {}", alldata.length);
        // FlowBytes to DataPackage
        DataPackage dp = new DataPackage();
        in.readerIndex(4);
        dp.setSize(in.readIntLE());// dataLength??
        dp.setMessageType(in.readByte());
        dp.setAreaId(in.readCharSequence(10, CharsetUtil.US_ASCII).toString());
        dp.setChnlNo(in.readCharSequence(10, CharsetUtil.US_ASCII).toString());
        dp.setIeFlag(in.readCharSequence(1, CharsetUtil.US_ASCII).toString());
        dp.setMark(in.readIntLE());// 4 byte(标识符)
        dp.setXmlSize(in.readIntLE());
        dp.setXml(in.readCharSequence(dp.getXmlSize(), Charset.forName("GB18030")).toString());
        if (dataLength > dp.getXmlSize() + 40) {
            // 图像处理
            /*
             * byte[] dst =null; in.getBytes(0, dst);
             */
            int picTotalLength = in.readIntLE();
            dp.setPicSize(picTotalLength);
            getPictureByData(in, picTotalLength, dp);
        }
        dp.setAllBytes(alldata); // 只用于保存日志，生成日志对象后可删除
        dp.setTimer(timer); // 添加计时

        return dp;
    }

    private boolean getPictureByData(ByteBuf b, int picTotalLength, DataPackage dp) {
        List<ContaPicture> contaList = new ArrayList<ContaPicture>();
        logger.debug("picTotalLength = {}", picTotalLength);
        logger.debug("readableBytes = {}", b.readableBytes());
        ContaPicture cp = null;
        while (b.isReadable(3)) {
            byte picType = b.readByte();// 图像方位
            int picLength = b.readIntLE();// 图像长度
            logger.debug("picType = {}", Integer.toHexString(picType));
            logger.debug("picLength = {}", picLength);

            byte[] picBytes = new byte[picLength];// 图像二进制流
            b.readBytes(picBytes, 0, picLength);// 图像二进制流

            cp = new ContaPicture();
            cp.setPicType(picType);
            cp.setPictureBytes(picBytes);
            contaList.add(cp);
        }
        dp.setContaPics(contaList);
        return true;
    }
}
