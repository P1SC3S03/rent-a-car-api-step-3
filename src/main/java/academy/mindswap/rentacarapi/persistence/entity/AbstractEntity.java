package academy.mindswap.rentacarapi.persistence.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * A generic entity to be used as a base for concrete types of entities
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Version
    private int version;
}
