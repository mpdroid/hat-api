package sortinghat;

import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.rule.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Based on https://stackoverflow.com/questions/19951880/drools-how-to-find-out-which-all-rules-were-matched
public class Explanator extends DefaultAgendaEventListener {

    private List<Match> matchList = new ArrayList<>();

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();

        String ruleName = rule.getName();
        Map<String, Object> ruleMetaDataMap = rule.getMetaData();

        matchList.add(event.getMatch());
        StringBuilder sb = new StringBuilder("Rule fired: " + ruleName);

        if (ruleMetaDataMap.size() > 0) {
            sb.append("\n  With [" + ruleMetaDataMap.size() + "] meta-data:");
            for (String key : ruleMetaDataMap.keySet()) {
                sb.append("\n    key=" + key + ", value="
                        + ruleMetaDataMap.get(key));
            }
        }

    }

    public boolean isRuleFired(String ruleName) {
        for (Match a : matchList) {
            if (a.getRule().getName().equals(ruleName)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        matchList.clear();
    }

    public final List<Match> getMatchList() {
        return matchList;
    }

    public String[] rulesFired() {
        String[] ret = new String[matchList.size()];
        int k = 0;
        for (Match match : matchList) {
            ret[k++] = match.getRule().getName();
        }
        return ret;
    }

}
