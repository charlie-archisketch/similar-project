package com.example.demo.domain.project.domain.child;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FromMap {

    private String mapFpDataId;

    private String addressId;

    private String fpDataId;

    private String mapFloorplanId;

    public FromMap() {
    }

    public FromMap(String mapFpDataId, String addressId, String fpDataId, String mapFloorplanId) {
        this.mapFpDataId = mapFpDataId;
        this.addressId = addressId;
        this.fpDataId = fpDataId;
        this.mapFloorplanId = mapFloorplanId;
    }

}
