package org.tytysh.fit_bot.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "import_history")
public class ImportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "version")
    Timestamp version;

    @Column(name = "type")
    String type;

    @Column(name = "sha")
    String sha;

    @Column(name = "body")
    byte[] body;

    @Column(name = "status")
    String status;

}
