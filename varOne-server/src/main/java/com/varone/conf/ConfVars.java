/**
 *
 */
package com.varone.conf;

/**
 * @author allen
 *
 */
public enum ConfVars {
	VARONE_HOME("varOne.home", "../"),
	VARONE_SERVER_ADDR("varOne.server.addr", "0.0.0.0"),
	VARONE_SERVER_PORT("varOne.server.port", 8080),
	VARONE_SERVER_CONTEXT_PATH("varOne.server.context.path", "/varOne-web"),
	VARONE_WAR("varOne.war", "../varOne-web/src/main/webapp"),
	VARONE_WAR_TEMPDIR("varOne.war.tempdir", "webapps");

	private String varName;
    @SuppressWarnings("rawtypes")
    private Class varClass;
    private String stringValue;
    private VarType type;
    private int intValue;
    private float floatValue;
    private boolean booleanValue;
    private long longValue;

	ConfVars(String varName, String varValue) {
	    this.varName = varName;
	    this.varClass = String.class;
	    this.stringValue = varValue;
	    this.intValue = -1;
	    this.floatValue = -1;
	    this.longValue = -1;
	    this.booleanValue = false;
	    this.type = VarType.STRING;
    }

	ConfVars(String varName, int intValue) {
		this.varName = varName;
		this.varClass = Integer.class;
		this.stringValue = null;
		this.intValue = intValue;
		this.floatValue = -1;
		this.longValue = -1;
		this.booleanValue = false;
		this.type = VarType.INT;
    }

    ConfVars(String varName, long longValue) {
	    this.varName = varName;
	    this.varClass = Integer.class;
	    this.stringValue = null;
	    this.intValue = -1;
	    this.floatValue = -1;
	    this.longValue = longValue;
	    this.booleanValue = false;
	    this.type = VarType.LONG;
    }

    ConfVars(String varName, float floatValue) {
	    this.varName = varName;
	    this.varClass = Float.class;
	    this.stringValue = null;
	    this.intValue = -1;
	    this.longValue = -1;
	    this.floatValue = floatValue;
	    this.booleanValue = false;
	    this.type = VarType.FLOAT;
    }

    ConfVars(String varName, boolean booleanValue) {
	    this.varName = varName;
	    this.varClass = Boolean.class;
	    this.stringValue = null;
	    this.intValue = -1;
	    this.longValue = -1;
	    this.floatValue = -1;
	    this.booleanValue = booleanValue;
	    this.type = VarType.BOOLEAN;
    }

    public String getVarName() {
      return varName;
    }

    @SuppressWarnings("rawtypes")
    public Class getVarClass() {
      return varClass;
    }

    public int getIntValue() {
      return intValue;
    }

    public long getLongValue() {
      return longValue;
    }

    public float getFloatValue() {
      return floatValue;
    }

    public String getStringValue() {
      return stringValue;
    }

    public boolean getBooleanValue() {
      return booleanValue;
    }

    public VarType getType() {
      return type;
    }

	enum VarType {
      STRING {
        @Override
        void checkType(String value) throws Exception {}
      },
      INT {
        @Override
        void checkType(String value) throws Exception {
          Integer.valueOf(value);
        }
      },
      LONG {
        @Override
        void checkType(String value) throws Exception {
          Long.valueOf(value);
        }
      },
      FLOAT {
        @Override
        void checkType(String value) throws Exception {
          Float.valueOf(value);
        }
      },
      BOOLEAN {
        @Override
        void checkType(String value) throws Exception {
          Boolean.valueOf(value);
        }
      };

      boolean isType(String value) {
        try {
          checkType(value);
        } catch (Exception e) {
          return false;
        }
        return true;
      }

      String typeString() {
        return name().toUpperCase();
      }

      abstract void checkType(String value) throws Exception;
    }
}
