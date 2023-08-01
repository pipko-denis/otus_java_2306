package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(new Comparator<>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.compare(o1.getScores(), o2.getScores());
        }
    });

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> customerEntry = map.firstEntry();
        if (customerEntry == null){
            throw new RuntimeException("Can't get smallest element of the empty map!");
        }
        Customer realCustomer = customerEntry.getKey();
        return Map.entry(
                new Customer(realCustomer.getId(), realCustomer.getName(), realCustomer.getScores()),
                customerEntry.getValue()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerEntry = map.higherEntry(customer);
        if (customerEntry == null) {
            return null;
        } else {
            Customer realCustomer = customerEntry.getKey();
            return Map.entry(
                    new Customer(realCustomer.getId(), realCustomer.getName(), realCustomer.getScores()),
                    customerEntry.getValue()
            );
        }
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
