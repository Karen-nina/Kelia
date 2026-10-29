import { timeAgo } from "./timeAgo";

describe("TimeAgo", () => {
  const realDateNow = Date.now;

  beforeAll(() => {
    jest.useFakeTimers();
    jest.setSystemTime(new Date("2025-07-10T12:00:00"));
  });

  afterAll(() => {
    jest.useRealTimers();
  });

  test("ReturnsInvalidDateForInvalidDateString", () => {
    expect(timeAgo("invalid-date")).toBe("Fecha inválida");
  });

  test("ReturnsSecondsAgoForLessThanAMinute", () => {
    const date = new Date(Date.now() - 45 * 1000).toISOString(); // 45 seg atrás
    expect(timeAgo(date)).toBe("hace 45 segundos");
  });

  test("ReturnsSingularSecondWhenOneSecondAgo", () => {
    const date = new Date(Date.now() - 1 * 1000).toISOString(); // 1 seg atrás
    expect(timeAgo(date)).toBe("hace 1 segundo");
  });

  test("ReturnsMinutesAgoForLessThanAnHour", () => {
    const date = new Date(Date.now() - 10 * 60 * 1000).toISOString(); // 10 mins atrás
    expect(timeAgo(date)).toBe("hace 10 minutos");
  });

  test("ReturnsSingularMinuteWhenOneMinuteAgo", () => {
    const date = new Date(Date.now() - 1 * 60 * 1000).toISOString(); // 1 min atrás
    expect(timeAgo(date)).toBe("hace 1 minuto");
  });

  test("ReturnsHoursAgoForLessThan3Hours", () => {
    const date = new Date(Date.now() - 2 * 3600 * 1000).toISOString(); // 2 horas atrás
    expect(timeAgo(date)).toBe("hace 2 horas");
  });

  test("ReturnsSingularHourWhenOneHourAgo", () => {
    const date = new Date(Date.now() - 1 * 3600 * 1000).toISOString(); // 1 hora atrás
    expect(timeAgo(date)).toBe("hace 1 hora");
  });

  test("ReturnsTodayWithFormattedTimeForMoreThan3HoursAgoSameDay", () => {
    const date = new Date(Date.now() - 5 * 3600 * 1000);
    const expectedTime = date.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
    });
    expect(timeAgo(date.toISOString())).toBe(`hoy a las ${expectedTime}`);
  });

  test("ReturnsYesterdayWithFormattedTimeForYesterday", () => {
    const yesterday = new Date(Date.now());
    yesterday.setDate(yesterday.getDate() - 1);
    const expectedTime = yesterday.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
    });
    expect(timeAgo(yesterday.toISOString())).toBe(`ayer a las ${expectedTime}`);
  });

  test("ReturnsDateWithoutYearForSameYearDifferentDay", () => {
    const someDay = new Date("2025-03-15T10:00:00");
    expect(timeAgo(someDay.toISOString())).toBe(
      someDay.toLocaleDateString("es-ES", { day: "numeric", month: "short" })
    );
  });

  test("ReturnsDateWithYearForDifferentYear", () => {
    const pastYear = new Date("2020-05-10T10:00:00");
    expect(timeAgo(pastYear.toISOString())).toBe(
      pastYear.toLocaleDateString("es-ES", {
        day: "numeric",
        month: "short",
        year: "numeric",
      })
    );
  });
});