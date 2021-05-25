package Jugadores;

public enum EstadoMovilidad {

	FINALIZADO {
		@Override
		public
		boolean siFinalizado() {

			return true;
		}
	},
	MOVIMIENTO_ILEGAL {
		@Override
		public
		boolean siFinalizado() {
			// TODO Auto-generated method stub
			return false;
		}
	},
	SEGUIR_EN_JAQUE {
		@Override
		public
		boolean siFinalizado() {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public abstract boolean siFinalizado();

}
