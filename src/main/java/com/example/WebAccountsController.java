package com.example;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebAccountsController {

	
	@Autowired
	protected DiscoveryClient discoveryClient;
	
	
	
	@RequestMapping("/myemlpoyee")
	public String goHome() throws RestClientException,IOException{
		
		List<ServiceInstance> instances=discoveryClient.getInstances("MYFIRST-EUREKA-SERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString();
		System.out.println("befor url"+ baseUrl);
			baseUrl=baseUrl+"/employee";
			System.out.println("after url"+ baseUrl);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try{
			//response=restTemplate.exchange(baseUrl,
					//HttpMethod.GET,getHeaders(), String.class);
			
			response=restTemplate.getForEntity(baseUrl,String.class);
			}catch (Exception ex)
			{
				System.out.println(ex);
			}
			System.out.println(response.getBody());
		
		return response.toString();
	}
	@RequestMapping("/hydrix")
	public String goHydrix() throws RestClientException, IOException {
		List<ServiceInstance> instances = discoveryClient.getInstances("HYSTRIX_CIRCUITBREAKER_FALLBACK");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString();
		System.out.println("befor url" + baseUrl);
		baseUrl = baseUrl + "/employee";
		System.out.println("after url" + baseUrl);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			// response=restTemplate.exchange(baseUrl,
			// HttpMethod.GET,getHeaders(), String.class);

			response = restTemplate.getForEntity(baseUrl, String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		System.out.println(response.getBody());

		return response.toString();
	}
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}
