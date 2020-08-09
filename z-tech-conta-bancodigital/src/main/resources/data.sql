CREATE DATABASE ztech_bancodigital;
insert into public.tipo_transacao values (1, 'DEPOSITO DINHEIRO');
insert into public.tipo_transacao values (2, 'RETIRADA DINHEIRO');
insert into public.tipo_transacao values (3, 'TRANSFERÊNCIA SAIDA DINHEIRO');
insert into public.tipo_transacao values (4, 'TRANSFERÊNCIA ENTRADA DINHEIRO');
insert into public.configuracao_porcentagem values (1, 'BONUS_DEPOSITO', '0.5');
insert into public.configuracao_porcentagem values (2, 'CUSTO_RETIRADA', '-1');
CREATE SEQUENCE NUMERO_CONTA START 10001;

--manter a compaiblidade com codigo mais novo
update public.configuracao_porcentagem set nome = 'BONUS_DEPOSITO' where id = 1;
update public.configuracao_porcentagem set porcentagem = '-1' where id = 2;
ALTER TABLE public.transacao DROP CONSTRAINT fk88bfifq5mcj9ycs59slqq1it1;
update public.transacao set contamovimentacao_id = contatransacao_id 
ALTER TABLE public.transacao DROP COLUMN contatransacao_id;
