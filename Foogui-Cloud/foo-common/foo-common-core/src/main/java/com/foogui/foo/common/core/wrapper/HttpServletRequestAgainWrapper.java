package com.foogui.foo.common.core.wrapper;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * http servlet请求二次包装器
 *
 * @author Foogui
 * @date 2023/05/12
 */
public class HttpServletRequestAgainWrapper extends HttpServletRequestWrapper {

    /**
     * 用于缓存输入流
     */
    private ByteArrayOutputStream cachedBytes;

    public HttpServletRequestAgainWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) {
            // 首次获取流时，将流放入 缓存输入流 中
            cachedBytes=new ByteArrayOutputStream();
            // 拿到父类中真正的request流进行复制
            IOUtils.copy(super.getInputStream(), cachedBytes);
        }

        // 从 缓存输入流 中获取流并返回
        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 自定义的servlet缓存输入流
     *
     * @author Foogui
     * @date 2023/05/12
     */
    private static class CachedServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream input;
        public CachedServletInputStream(byte[] bytes) {
            input=new ByteArrayInputStream(bytes);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return input.read();
        }
    }
}
