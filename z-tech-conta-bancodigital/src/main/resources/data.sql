insert into public.tipo_transacao values (1, 'DEPOSITO DINHEIRO');
insert into public.tipo_transacao values (2, 'RETIRADA DINHEIRO');
insert into public.tipo_transacao values (3, 'TRANSFERÊNCIA SAIDA DINHEIRO');
insert into public.tipo_transacao values (4, 'TRANSFERÊNCIA ENTRADA DINHEIRO');
insert into public.configuracao_porcentagem values (1, 'BONUS_DEPOSITO', '0.5');
insert into public.configuracao_porcentagem values (2, 'CUSTO_RETIRADA', '-1');
CREATE SEQUENCE NUMERO_CONTA START 10001;
