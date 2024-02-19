package capstone.examlab.exam.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@Data
@Document(indexName = "products")
public class Product {

    @Id
    private int id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Double)
    private double price;
    @Field(type = FieldType.Text)
    private String categoryName;
}