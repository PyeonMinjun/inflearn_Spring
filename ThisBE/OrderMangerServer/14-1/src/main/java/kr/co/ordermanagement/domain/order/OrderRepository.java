package kr.co.ordermanagement.domain.order;

public interface OrderRepository {
    void save();

    void add(Order order);
}
