INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_CIDADE');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (grupo, permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (grupo, permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (usuario, grupo) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'admin@brewer.com'), 1);
