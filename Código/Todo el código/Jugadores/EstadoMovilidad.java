package Jugadores;

public enum EstadoMovilidad {

	FINALIZADO {
		@Override
		boolean siFinalizado() {

			return true;
		}
	},
	MOVIMIENTO_ILEGAL {
		@Override
		boolean siFinalizado() {
			// TODO Auto-generated method stub
			return false;
		}
	},
	SEGUIR_EN_JAQUE {
		@Override
		boolean siFinalizado() {
			// TODO Auto-generated method stub
			return false;
		}
	};

	abstract boolean siFinalizado();

}
