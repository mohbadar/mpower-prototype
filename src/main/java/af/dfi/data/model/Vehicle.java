package af.dfi.data.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "vehicle")
public class Vehicle {

    @Id
    private String id;

    @Field(value = "plate_number")
    private String plateNumber;


    @Field(value = "vehicle_color")
    private String vehicleColor;


    @Field(value = "vehicle_make")
    private String vehicleMake;


    @Field(value = "vehicle_make_model")
    private String vehicleMakeModel;


    @Field(value = "vehicle_type")
    private String vehicleType;


    @Field(value = "vehicle_orientation")
    private String vehicleOrientation;

    @Field(value = "vehicle_year")
    private String vehicleYear;


    @DBRef
    private Agent agent;

    @DBRef
    private Camera camera;

    @CreationTimestamp
    @Field(value = "created_at")
    private Timestamp regdate;

    @UpdateTimestamp
    @Field(value = "updated_at")
    private Timestamp updatedate;

}
