package com.pttem.ecommerce.productmanagement.config;

import com.pttem.ecommerce.productmanagement.messaging.Receiver;
import filter.AuthFilter;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Configuration
public class ProductManagementConfiguration {
  public static final String topicExchangeName = "spring-boot-exchange";
  public static final String queueName = "spring-boot";
  private static final String BASE_PACKAGE = "com.pttem.ecommerce.productmanagement";
  private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";
  private static final String CONTROLLER_PATH_REGEX = "/rest.*";
  @Autowired private List<HandlerMapping> handlerMappings;


  @Bean
  public FilterRegistrationBean<AuthFilter> authFilter() {
    FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
    AuthFilter authFilter = new AuthFilter();
    authFilter.setHandlerMappings(handlerMappings);
    registrationBean.setFilter(authFilter);
    return registrationBean;
  }

  @Bean
  public CorsConfiguration corsss(){
    return new CorsConfiguration().applyPermitDefaultValues();
  }

  @Bean
  public Docket api() throws IOException, org.codehaus.plexus.util.xml.pull.XmlPullParserException {
    MavenXpp3Reader reader = new MavenXpp3Reader();
    Model model = reader.read(new FileReader("pom.xml"));
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(
            RequestHandlerSelectors.basePackage("com.pttem.ecommerce.productmanagement.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(
            new ApiInfo(
                "Product Service Api Documentation",
                "Documentation automatically generated",
                model.getParent().getVersion(),
                null,
                    "Mehmet Karakaya", null,
                null));
  }

  @Bean
  Queue queue() {
    return new Queue(queueName, false);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(topicExchangeName);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
  }

  @Bean
  SimpleMessageListenerContainer container(
      ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Receiver receiver) {
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }
}
