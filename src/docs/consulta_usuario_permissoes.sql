select u.email usuario,
	group_concat(substring(p.nome, 6) order by p.nome separator ',') permissao

from usuario u, 
	usuario_grupo ug,
	grupo g,
	grupo_permissao gp,
	permissao p

where ug.usuario = u.codigo
	and ug.grupo = g.codigo
	and g.codigo = gp.grupo
	and gp.permissao = p.codigo

group by usuario