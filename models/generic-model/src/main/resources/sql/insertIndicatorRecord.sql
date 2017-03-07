insert into IndicatorRecord(
	uid,
	indicatorValue,
	
	timestmp,
	timestmpyyyy,
	timestmpyyyyMM,
	timestmpyyyyMMdd,
	timestmpyyyyMMddHH,
	
	frequencyGroupValue,
	indicatorClass
	
	) values (
	:#${body.uid},
	:#${body.indicatorValue},
	:#${body.timestmp},
	:#${date:header.eventTimestmp:yyyy},
	:#${date:header.eventTimestmp:yyyyMM},
	:#${date:header.eventTimestmp:yyyyMMdd},
	:#${date:header.eventTimestmp:yyyyMMddHH},
	:#${body.frequencyGroupValue},
	:#${body.indicatorClass}
	
	);