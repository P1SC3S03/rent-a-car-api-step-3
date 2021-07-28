package academy.mindswap.rentacarapi.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The rent entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rents")
public class RentEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId", nullable = false)
    private CarEntity carEntity;

    @Column(nullable=false)
    private Date expectedBeginDate;

    private Date beginDate;

    @Column(nullable=false)
    private Date expectedEndDate;

    private Date endDate;

    @Column(nullable=false)
    private BigDecimal expectedPrice;

    private BigDecimal finalPrice;
}
