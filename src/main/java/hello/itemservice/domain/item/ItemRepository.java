package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 사용 주의. 실제는 static 사용하면 안 됨.
    private static long sequence = 0L; // staic
    // 동시에 여러 스레드가 접근할 수 있으면, hashmap을 쓰면 안되고 CuncurrentHashMap을 써야 한다.
    // 시퀀스도 그냥 쓰면 안됨.

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        // 프로젝트 규모가 커지면 Item대신 이거용 DTO를 만들어야 함.
        // 중복이냐 명확성이냐 하면 항상 명확성을 챙기는 것이 좋음.
        // 이유: 여기서 Item에 id가 있는데 다른 개발자가 getId, setId도 해버릴 수 있다.
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
