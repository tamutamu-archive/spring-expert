CREATE TABLE venda (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_criacao DATETIME NOT NULL,
    valor_frete DECIMAL(10,2),
    valor_desconto DECIMAL(10,2),
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacao VARCHAR(200),
    data_hora_entrega DATETIME,
    cliente BIGINT(20) NOT NULL,
    vendedor BIGINT(20) NOT NULL,
    FOREIGN KEY (cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (vendedor) REFERENCES usuario(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_venda (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    cerveja BIGINT(20) NOT NULL,
    venda BIGINT(20) NOT NULL,
    FOREIGN KEY (cerveja) REFERENCES cerveja(codigo),
    FOREIGN KEY (venda) REFERENCES venda(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;