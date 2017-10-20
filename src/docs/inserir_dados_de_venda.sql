/* Inserir dados aleatórios de vendas para 
 * ter vendas suficientes para gerar relatórios de vendas
 */

insert into venda (data_criacao, valor_total, status, cliente, vendedor) 
  values (
    FROM_UNIXTIME(ROUND((RAND() * (1473292800 - 1454284800) + 1454284800)))
  , round(rand() * 10000, 2)
  , 'EMITIDA'
  , round(rand() * 6) + 1
  , round(rand() * 4) + 10);