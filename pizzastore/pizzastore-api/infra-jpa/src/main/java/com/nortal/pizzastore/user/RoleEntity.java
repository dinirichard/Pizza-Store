package com.nortal.pizzastore.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
class RoleEntity {

  @Id
  @Column(name = "code", nullable = false)
  private String code;
}
