package ua.palii.nettytest.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by VVV on 05.08.2015.
 */
public class StatisticsHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Statistics statistics = Statistics.getInstance();

    private Traffic traffic;

    private RequestInfo requestInfo;

    private ConnectionInfo connectionInfo;

    /**
     * After channel is active update count of open connections
     * and start to count traffic
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        statistics.addConnection();
        traffic = new Traffic(ctx);
    }

    /**
     * Update statistic
     *
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        requestInfo = new RequestInfo(ctx, request);
        connectionInfo = new ConnectionInfo(requestInfo);
        statistics.addCountRequest(requestInfo);
        request.retain();
        ctx.fireChannelRead(request);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        traffic.stop();
        connectionInfo.setTraffic(traffic);
        statistics.addLastConnection(connectionInfo);
        statistics.removeConnection();
    }

}
