
CREATE TABLE IF NOT EXISTS IndicatorRecord(
	uid VARCHAR PRIMARY KEY NOT NULL,
	indicatorValue numeric,
	timestmp TIMESTAMP,
	timestmpyyyy VARCHAR(4),
	timestmpyyyyMM VARCHAR(6),
	timestmpyyyyMMdd VARCHAR(8),
	timestmpyyyyMMddHH VARCHAR(10),
	
	frequencyGroupValue numeric,
	indicatorClass VARCHAR(255)
);
CREATE INDEX IF NOT EXISTS timestmpIdx ON IndicatorRecord(timestmp);
CREATE INDEX IF NOT EXISTS timestmpyyyy ON IndicatorRecord(timestmpyyyy);
CREATE INDEX IF NOT EXISTS timestmpyyyyMM ON IndicatorRecord(timestmpyyyyMM);
CREATE INDEX IF NOT EXISTS timestmpyyyyMMdd ON IndicatorRecord(timestmpyyyyMMdd);
CREATE INDEX IF NOT EXISTS timestmpyyyyMMddHH ON IndicatorRecord(timestmpyyyyMMddHH);

CREATE INDEX IF NOT EXISTS frequencyGroupValueIdx ON IndicatorRecord(frequencyGroupValue);
CREATE INDEX IF NOT EXISTS indicatorClassIdx ON IndicatorRecord(indicatorClass);