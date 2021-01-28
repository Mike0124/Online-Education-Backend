package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "live_address")
public class LiveAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "live_address_id")
    Integer liveAddressId;

    @Column(name = "live_address")
    String liveAddress;
}
