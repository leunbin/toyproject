(function () {
  const $ = (s) => document.querySelector(s);
  const out = $("#output");

  const pick = (arr) => arr[Math.floor(Math.random() * arr.length)];
  const range = (a, b) => Array.from({length: b - a + 1}, (_, i) => String.fromCharCode(a + i));

  const LOWER = range(97,122);
  const UPPER = range(65,90);
  const NUM   = range(48,57);
  const SYM   = Array.from("!@#$%^&*()-_=+[]{};:,.?/");

  function secureRandom(max) {
    if (window.crypto?.getRandomValues) {
      const buf = new Uint32Array(1);
      window.crypto.getRandomValues(buf);
      return buf[0] % max;
    }
    return Math.floor(Math.random() * max);
  }

  function generate(opts) {
    const pools = [];
    if (opts.lower) pools.push(LOWER);
    if (opts.upper) pools.push(UPPER);
    if (opts.number) pools.push(NUM);
    if (opts.symbol) pools.push(SYM);
    if (pools.length === 0) return "";

    // 각 카테고리 최소 1자 보장
    const base = pools.map(p => p[secureRandom(p.length)]);

    // 나머지 채우기
    while (base.length < opts.length) {
      const pool = pools[secureRandom(pools.length)];
      base.push(pool[secureRandom(pool.length)]);
    }

    // 셔플 (Fisher–Yates)
    for (let i = base.length - 1; i > 0; i--) {
      const j = secureRandom(i + 1);
      [base[i], base[j]] = [base[j], base[i]];
    }
    return base.join("");
  }

  $("#generate").addEventListener("click", () => {
    const length = Math.max(4, Math.min(64, parseInt($("#length").value || "12", 10)));
    const pwd = generate({
      length,
      lower: $("#lower").checked,
      upper: $("#upper").checked,
      number: $("#number").checked,
      symbol: $("#symbol").checked
    });
    out.textContent = pwd || "옵션을 하나 이상 선택하세요";
    out.dataset.value = pwd;
  });

  $("#copy").addEventListener("click", async () => {
    const v = out.dataset.value || out.textContent || "";
    if (!v || v.startsWith("옵션")) return;
    try {
      await navigator.clipboard.writeText(v);
      const old = out.textContent;
      out.textContent = "클립보드에 복사되었습니다!";
      setTimeout(() => out.textContent = old, 1000);
    } catch {
      // fallback
      const ta = document.createElement("textarea");
      ta.value = v; document.body.appendChild(ta);
      ta.select(); document.execCommand("copy");
      document.body.removeChild(ta);
    }
  });

  // 출력 클릭으로도 복사
  out.addEventListener("click", () => $("#copy").click());
})();
