package com.abhinav.order_service.service;

import com.abhinav.order_service.client.ProductClient;
import com.abhinav.order_service.client.UserClient;
import com.abhinav.order_service.dto.OrderDto;
import com.abhinav.order_service.dto.ProductDto;
import com.abhinav.order_service.dto.UserDto;
import com.abhinav.order_service.entity.Order;
import com.abhinav.order_service.exception.ExternalServiceException;
import com.abhinav.order_service.exception.ResourceNotFoundException;
import com.abhinav.order_service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, UserClient userClient,
                            ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // Validate User
        try {
            UserDto userDto = userClient.getUserById(orderDto.getUserId());
            if (userDto == null) {
                throw new ExternalServiceException("User validation failed.");
            }
        } catch (Exception e) {
            throw new ExternalServiceException("User ID " + orderDto.getUserId() + " not found or User Service is down.");
        }

        // Validate Product
        try {
            ProductDto productDto = productClient.getProductById(orderDto.getProductId());
            if (productDto == null) {
                throw new ExternalServiceException("Product validation failed.");
            }
        } catch (Exception e) {
            throw new ExternalServiceException("Product ID " + orderDto.getProductId() + " not found or Product Service is down.");
        }

        Order order = modelMapper.map(orderDto, Order.class);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", id)
        );
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
