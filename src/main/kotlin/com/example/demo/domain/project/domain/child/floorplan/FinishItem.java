package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.domain.project.domain.enums.FinishTargetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FinishItem {
    private String floorArchiId;

    private String roomArchiId;

    private String wallArchiId;

    private String productId;

    private int price;

    private int quantity;

    private double loss;

    private FinishTargetType targetType;

    private double targetArea;

	private int tileQuantity;

	public FinishItem(String floorArchiId, String roomArchiId, String wallArchiId, String productId, int price, int quantity, double loss, double targetArea, FinishTargetType targetType, int tileQuantity) {
        this.floorArchiId = floorArchiId;
        this.roomArchiId = roomArchiId;
        this.wallArchiId = wallArchiId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.loss = loss;
        this.targetArea = targetArea;
        if (targetType != null) {
            this.targetType = targetType;
        }

		this.tileQuantity = tileQuantity;
    }

}
