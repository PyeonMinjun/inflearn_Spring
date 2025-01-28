package kr.co.ordermanagement.infrastructure;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ListOrderRepository implements OrderRepository {

    @Override
    public void save() {

    }

    @Override
    public void add(Order order) {

    }
}
