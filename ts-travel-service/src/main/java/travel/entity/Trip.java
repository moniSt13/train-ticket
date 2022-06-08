package travel.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Trip {
    @Valid
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "tripUuid")
    private String id;

    @Embedded
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    private String routeId;

    private Date startingTime;

    @Valid
    @NotNull
    private String startingStationId;

    @Valid
    private String stationsId;

    @Valid
    @NotNull
    private String terminalStationId;

    @Valid
    @NotNull
    private Date endTime;

    public Trip(TripId tripId, String trainTypeId, String startingStationId, String stationsId, String terminalStationId, Date startingTime, Date endTime) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startingStationId = startingStationId;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
        this.startingStationId = "";
        this.terminalStationId = "";
        this.endTime = new Date();
    }

    public Trip(){
        //Default Constructor
        this.id = UUID.randomUUID().toString();
        this.trainTypeId = "";
        this.startingStationId = "";
        this.terminalStationId = "";
        this.endTime = new Date();
    }

}