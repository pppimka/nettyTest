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

    private final String HELLO_PAGE = "hello.html";

    private final String NOT_FOUND = "notFound.html";

    private final String STATISTICS = "statistics.html";


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
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (path.equals("/hello")) {
            sleep();
            requestInfo.writeResponse(HELLO_PAGE);
        } else if (path.equals("/status")) {
            requestInfo.writeResponse(STATISTICS);
        } else if (path.equals("/redirect") && requestInfo.getUrl() != null) {

            requestInfo.redirect(requestInfo.getUrl());
        } else {
            requestInfo.writeResponse(NOT_FOUND);
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
