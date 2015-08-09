package ua.palii.nettytest.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;

/**
 * Created by VVV on 03.08.2015.
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final int TIMEOUT = 10000;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        RequestInfo requestInfo = new RequestInfo(ctx, request);
        selectPage(requestInfo);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void selectPage(RequestInfo requestInfo) {
        String path = requestInfo.getPath();
        if (path.equals("/hello")) {
            sleep();
            requestInfo.writeResponse(Pages.getHelloPage());
        } else if (path.equals("/status")) {
            requestInfo.writeResponse(Pages.getStatisticsPage());
        } else if (path.equals("/redirect") && requestInfo.getUrl() != null) {

            requestInfo.redirect(requestInfo.getUrl());
        } else {
            requestInfo.writeResponse(Pages.getNotFoundPage());
        }
    }

    private void sleep() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
