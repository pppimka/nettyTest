package ua.palii.nettytest.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by VVV on 05.08.2015.
 */
public class RequestInfo {

    private final String PARAM_URL = "url";

    private Statistics statistics = Statistics.getInstance();
    private ChannelHandlerContext ctx;
    private FullHttpRequest msg;
    private QueryStringDecoder queryStringDecoder;


    public RequestInfo(ChannelHandlerContext ctx, FullHttpRequest msg) {
        this.ctx = ctx;
        this.msg = msg;
        queryStringDecoder = new QueryStringDecoder(getUri());
    }

    public void writeResponse(String str) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, OK, Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");

        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }


    public void redirect(String url) {
        if (!url.matches("http://\\S*") || !url.matches("https://\\S*")) {
            url = "http://" + url;
        }
        statistics.addRedirect(url);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, FOUND);
        response.headers().set(HttpHeaders.Names.LOCATION, url);
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * get url to redirect from parameters
     *
     * @return url or null if there are no url's
     */
    public String getUrl() {
        List<String> url = queryStringDecoder.parameters().get(PARAM_URL);
        if (url != null) {
            return url.get(0);
        }
        return null;
    }

    public String getIP() {
        return (((InetSocketAddress) ctx.channel().remoteAddress()).getHostString());
    }


    public String getUri() {
        return msg.getUri();
    }

    public String getPath() {
        return queryStringDecoder.path();
    }
}
