package brewer.model;

public enum Origem {

	NACIONAL("Nacional"),
	IMPORTADA("Importada");
	
	private String descricao; 
	
	Origem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
