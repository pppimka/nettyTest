package ua.palii.nettytest.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by VVV on 03.08.2015.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     *   The delay between two computations of performances for channels or 0 if no stats are to be computed.
     */
    private static final long CHECK_INTERVAL = 0;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        EventExecutorGroup executorGroup = new DefaultEventExecutorGroup(10);

        pipeline.addLast("traffic handler", new ChannelTrafficShapingHandler(CHECK_INTERVAL));
        pipeline.addLast("HttpRequestDecoder and HttpResponseEncoder", new HttpServerCodec());
        pipeline.addLast("object aggregator", new HttpObjectAggregator(
                HttpObjectAggregator.DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS * 512));
        pipeline.addLast("statistics handler", new StatisticsHandler());
        pipeline.addLast(executorGroup, "handler", new ServerHandler());

    }
}
