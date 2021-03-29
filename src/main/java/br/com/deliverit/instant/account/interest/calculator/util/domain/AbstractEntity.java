package br.com.deliverit.instant.account.interest.calculator.util.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@SuperBuilder
@Data
@EqualsAndHashCode(of = "id")
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -8268688793606761666L;

    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PUBLIC)
    private UUID id;

    @Tolerate
    public AbstractEntity() {
    }

    public void generateId() {
        if (Objects.isNull(this.id))
            this.setId(UUID.randomUUID());
    }
}
