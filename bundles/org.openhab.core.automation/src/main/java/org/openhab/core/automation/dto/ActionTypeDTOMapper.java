/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.core.automation.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.automation.type.ActionType;
import org.openhab.core.automation.type.CompositeActionType;
import org.openhab.core.config.core.dto.ConfigDescriptionDTOMapper;

/**
 * This is a utility class to convert between the respective object and its DTO.
 *
 * @author Markus Rathgeb - Initial contribution
 * @author Ana Dimova - extends Action Module type DTOs with composites
 */
@NonNullByDefault
public class ActionTypeDTOMapper extends ModuleTypeDTOMapper {

    public static ActionTypeDTO map(final ActionType actionType) {
        return map(actionType, new ActionTypeDTO());
    }

    public static CompositeActionTypeDTO map(final CompositeActionType actionType) {
        final CompositeActionTypeDTO actionTypeDto = map(actionType, new CompositeActionTypeDTO());
        actionTypeDto.children = ActionDTOMapper.map(actionType.getChildren());
        return actionTypeDto;
    }

    public static ActionType map(CompositeActionTypeDTO actionTypeDto) {
        if (actionTypeDto.children == null || actionTypeDto.children.isEmpty()) {
            return new ActionType(actionTypeDto.uid, ConfigDescriptionDTOMapper.map(actionTypeDto.configDescriptions),
                    actionTypeDto.label, actionTypeDto.description, actionTypeDto.tags, actionTypeDto.visibility,
                    actionTypeDto.inputs, actionTypeDto.outputs);
        } else {
            return new CompositeActionType(actionTypeDto.uid,
                    ConfigDescriptionDTOMapper.map(actionTypeDto.configDescriptions), actionTypeDto.label,
                    actionTypeDto.description, actionTypeDto.tags, actionTypeDto.visibility, actionTypeDto.inputs,
                    actionTypeDto.outputs, ActionDTOMapper.mapDto(actionTypeDto.children));
        }
    }

    public static List<ActionTypeDTO> map(final @Nullable Collection<ActionType> types) {
        if (types == null) {
            return List.of();
        }
        final List<ActionTypeDTO> dtos = new ArrayList<>(types.size());
        for (final ActionType type : types) {
            if (type instanceof CompositeActionType actionType) {
                dtos.add(map(actionType));
            } else {
                dtos.add(map(type));
            }
        }
        return dtos;
    }

    private static <T extends ActionTypeDTO> T map(final ActionType actionType, final T actionTypeDto) {
        fillProperties(actionType, actionTypeDto);
        actionTypeDto.inputs = actionType.getInputs();
        actionTypeDto.outputs = actionType.getOutputs();
        return actionTypeDto;
    }
}
