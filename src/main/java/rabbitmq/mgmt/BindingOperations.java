package rabbitmq.mgmt;

import com.google.common.base.Optional;
import rabbitmq.mgmt.model.Binding;
import rabbitmq.mgmt.model.Exchange;
import rabbitmq.mgmt.model.Queue;

import java.util.Collection;


public class BindingOperations extends BaseFluent {

	public BindingOperations(HttpContext httpContext, RabbitMgmtService mgmtService) {
		
		super(httpContext, mgmtService);
	}

	public Optional<Collection<Binding>> all(){
		
		return HTTP.GET("/bindings", BINDING_COLLECTION);
	}
	
	public Optional<Collection<Binding>> all(String vhost){
		
		return HTTP.GET(String.format("/bindings/%s", encodeSlashes(vhost)), BINDING_COLLECTION);
	}
	
	public Optional<Collection<Binding>> get(String vhost, String exchangeName, String queueName){
		
		return HTTP.GET(
			String.format("/bindings/%s/e/%s/q/%s", encodeSlashes(vhost), exchangeName, queueName), BINDING_COLLECTION);
	}
	
	public Optional<Binding> get(String vhost, String exchangeName, String queueName, String props){
		
		return HTTP.GET(
			String.format("/bindings/%s/e/%s/q/%s/%s", encodeSlashes(vhost), exchangeName, queueName, props), BINDING);
	}
	
	public BindingOperations create(Binding binding){
		
		String url = String.format(
			"/bindings/%s/e/%s/q/%s", encodeSlashes(binding.getVhost()), binding.getSource(), binding.getDestination());
		
		HTTP.POST(url, binding);
		
		return this;
	}
	
	public BindingOperations create(Exchange exchange, Queue queue, String routingKey){
		
		Binding binding = Binding.createBinding(exchange, queue, routingKey);
		
		return this.create(binding);
	}
	
	public BindingOperations create(Exchange exchange, Exchange destExchange, String routingKey){
		
		Binding binding = Binding.createBinding(exchange, destExchange, routingKey);
		
		return this.create(binding);
	}
	
	public BindingOperations delete(String vhost, String exchangeName, String queueName, String props){
		
		HTTP.DELETE(
			String.format("/bindings/%s/e/%s/q/%s/%s", encodeSlashes(vhost), exchangeName, queueName, props));
		
		return this;
	}
}
