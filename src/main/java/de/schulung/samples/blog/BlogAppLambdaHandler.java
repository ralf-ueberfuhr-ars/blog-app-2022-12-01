package de.schulung.samples.blog;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused") // registered as lambda handler
public class BlogAppLambdaHandler implements RequestStreamHandler {
    private final SpringBootLambdaContainerHandler<Object, AwsProxyResponse> handler;

    public BlogAppLambdaHandler() throws ContainerInitializationException {
        handler = new SpringBootProxyHandlerBuilder<>()
                .defaultProxy()
                .asyncInit()
                .springBootApplication(BlogAppApplication.class)
                .profiles("aws", "unsecure")
                .buildAndInitialize();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }

}
