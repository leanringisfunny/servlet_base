package hello.itemservice.domain.item;

public enum ItemType
{
    FOOD("음식"),BOOK("책"), ETC("기타");
    private String description;
    ItemType(String description) {
        this.description=description;
    }
}
