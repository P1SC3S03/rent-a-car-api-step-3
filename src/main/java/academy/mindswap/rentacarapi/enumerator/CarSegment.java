package academy.mindswap.rentacarapi.enumerator;

import java.math.BigDecimal;

/**
 * Enum of car segments with daily price
 */
public enum CarSegment {
    SMALL("Small car", new BigDecimal("30")),
    SMALL_VAN("Small van", new BigDecimal("40")),
    FAMILY("Family car", new BigDecimal("50")),
    VAN("Commercial van", new BigDecimal("60")),
    PREMIUM("Premium car", new BigDecimal("120"));

    private String name;
    private BigDecimal dailyPrice;

    CarSegment(String name, BigDecimal pricePerDay) {
        this.name = name;
        this.dailyPrice = pricePerDay;
    }

    /**
     * Get enum name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get daily price
     * @return the price
     */
    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }
}
